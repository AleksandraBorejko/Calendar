package com.calendar.controller;

import java.io.IOException;
import java.sql.SQLException;

import com.calendar.Calendar;
import com.calendar.entity.Note;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTimePicker;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 *
 * @author Aleksandra Borejko
 *
 */
public class NoteController {
    @FXML
    private Button addNoteButton;
    @FXML
    private Button updateNoteButton;
    @FXML
    private Button cancelButton;
    @FXML
    private TextField titleField;
    @FXML
    private ColorPicker colorPicker;
    @FXML
    private TextField locationField;
    @FXML
    private JFXDatePicker datePicker;
    @FXML
    private JFXTimePicker timePicker;
    @FXML
    private TextArea detailsField;
    @FXML
    private CalendarController calendarController;

    private Note note;

    public void setCalendarController(CalendarController calendarController) {
        this.calendarController = calendarController;
    }

    public void initialize() {
        note = CalendarController.getSelectedNote();
        if (note == null) {
            note = new Note();
            addNoteButton.setVisible(true);
        } else {
            updateNoteButton.setVisible(true);
        }

        titleField.textProperty().bindBidirectional(note.titleProperty());
        colorPicker.valueProperty().bindBidirectional(note.colorProperty());
        locationField.textProperty().bindBidirectional(note.locationProperty());
        datePicker.valueProperty().bindBidirectional(note.dateProperty());
        timePicker.valueProperty().bindBidirectional(note.timeProperty());
        detailsField.textProperty().bindBidirectional(note.detailsProperty());

    }

    public void addNote() {
        if (validateNote()) {
            insertNoteToDatabase();
            closeWindowAndRefresh(addNoteButton);
        }
    }

    public void updateNote() {
        if (validateNote()) {
            updateNoteInDatabase();
            closeWindowAndRefresh(updateNoteButton);
        }
    }

    private boolean validateNote() {
        Alert alert = new Alert(AlertType.WARNING);
        if (note.getTitle() == null && note.getDate() == null) {
            alert.setContentText("Please enter the title and the date");
            alert.show();
            alert.setOnHidden((e) -> {
                setErrorBorder(titleField);
                setErrorBorder(datePicker);
            });
            return false;
        } else if (note.getDate() == null) {
            alert.setContentText("Please select the date");
            alert.show();
            alert.setOnHidden((e) -> setErrorBorder(datePicker));
            return false;
        } else if (note.getTitle() == null) {
            alert.setContentText("Please enter the title");
            alert.show();
            alert.setOnHidden((e) -> setErrorBorder(titleField));
            return false;
        }
        return true;
    }

    private void setErrorBorder(javafx.scene.layout.Region node) {
        node.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, new CornerRadii(3), null)));
    }

    private void closeWindowAndRefresh(Button button) {
        Stage stage = (Stage) button.getScene().getWindow();
        stage.close();
        calendarController.refreshCalendar(note.getDate());
    }

    private void updateNoteInDatabase() {
        try {
            Calendar.getNoteDao().updateNote(note, note.getId());
        } catch (IOException | SQLException e) {
            System.out.println("Couldn't update the note: " + e.getMessage());
        }
    }

    private void insertNoteToDatabase() {
        try {
            Calendar.getNoteDao().insertNote(note);
        } catch (IOException | SQLException e) {
            System.out.println("Couldn't insert the note: " + e.getMessage());
        }
    }

    public void closeAddNoteWindow() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
}
