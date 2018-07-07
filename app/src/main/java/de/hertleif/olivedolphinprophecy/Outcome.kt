package de.hertleif.olivedolphinprophecy

enum class Outcome {
    Yes,
    No;

    fun texts(): Set<String> {
        return if (this == Outcome.Yes) {
            setOf("Yes", "Yep", "Sure", "Sounds good", "Go ahead", "Certainly", "Yes please", "Oh yes!", "Very well", "Absolutely", "Affirmative", "Alright", "Very well", "By all means", "Indeed", "Okey-dokey", "Aye aye!", "Fine", "Gladly", "Naturally", "Sure thing", "Totally")
        } else {
            setOf("No", "Nope", "Nay", "Meh", "Never", "Oh god no", "Hell no", "Stop", "Most certainly not", "Not at all", "No way", "Negative", "Under no circumstances", "Uh-uh", "by no means", "No thanks", "Out of the question", "Not for all the tea in China", "Not in a million years")
        }
    }
}