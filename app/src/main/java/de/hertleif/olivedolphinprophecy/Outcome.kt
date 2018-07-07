package de.hertleif.olivedolphinprophecy

enum class Outcome {
    Yes,
    No;

    fun texts(): Set<String> {
        return if (this == Outcome.Yes) {
            setOf("Yes", "Yep", "Sure", "Sounds good", "Go ahead", "Certainly", "Yes please", "Oh yes!", "Very well", "Absolutely", "Affirmative")
        } else {
            setOf("No", "Nope", "Nay", "Meh", "Never", "Oh god no", "Hell no", "Stop", "Most certainly not", "Not at all", "No way", "Negative")
        }
    }
}