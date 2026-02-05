# How to Split a Word into an Array of Characters in Ruby

Ruby provides several methods to split a word (string) into an array of individual characters.

## Method 1: Using `chars` (Recommended)

The `chars` method is the most straightforward and idiomatic way to split a string into an array of characters:

```ruby
word = "hello"
characters = word.chars
# => ["h", "e", "l", "l", "o"]
```

## Method 2: Using `split` with Empty String

You can use the `split` method with an empty string as the delimiter:

```ruby
word = "hello"
characters = word.split('')
# => ["h", "e", "l", "l", "o"]
```

## Method 3: Using `each_char` with an Array

The `each_char` method iterates over each character and can be combined with array operations:

```ruby
word = "hello"
characters = []
word.each_char { |char| characters << char }
# => ["h", "e", "l", "l", "o"]
```

Or more concisely:

```ruby
word = "hello"
characters = word.each_char.to_a
# => ["h", "e", "l", "l", "o"]
```

## Method 4: Using `split` with Regex Pattern

You can convert a string to an array using `split` with an empty regex pattern:

```ruby
word = "hello"
characters = word.split(//)
# => ["h", "e", "l", "l", "o"]
```

## Examples with Different Use Cases

### Working with Unicode Characters

```ruby
word = "café"
characters = word.chars
# => ["c", "a", "f", "é"]
```

### Iterating Over Characters

```ruby
word = "hello"
word.chars.each do |char|
  puts char.upcase
end
# Output:
# H
# E
# L
# L
# O
```

### Filtering Characters

```ruby
word = "hello"
vowels = word.chars.select { |char| ['a', 'e', 'i', 'o', 'u'].include?(char) }
# => ["e", "o"]
```

### Reversing Characters

```ruby
word = "hello"
reversed = word.chars.reverse.join
# => "olleh"
```

## Performance Comparison

For most use cases, `chars` is the recommended method as it is:
- Clear and readable
- Idiomatic Ruby
- Properly handles Unicode characters
- Good performance

## Summary

**Best Practice**: Use `word.chars` to split a word into an array of characters in Ruby.

```ruby
"example".chars  # => ["e", "x", "a", "m", "p", "l", "e"]
```
