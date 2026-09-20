package jp.ahoashi.guitarchord.entity

// TODO: 未利用部分のコード追加
enum class TYPE(
    val displayName: String,
) {
    MAJOR("Major"),
    MINOR("minor"),
    M7("maj7"),
    SEVENTH("7"),
    MINOR7("m7"),
    MM7("mM7"),
    SUS4("sus4"),
    SEVEN_SUS4("7sus4"),
    ADD9("add9"),
    MADD9("madd9"),
//    SIX,
//    MINOR6,
//    AUG,
//    DIM,
//    DIM7,
//    MINOR7B5,
}
