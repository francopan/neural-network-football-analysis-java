package dataset.enums;

public enum MatchResult {
    HomeWin('H'),
    Draw('D'),
    AwayWin('W');

    private final char value;

    MatchResult(char value) {
        this.value = value;
    }

    public static MatchResult fromChar(char c) {
        switch (c) {
            case 'H':
                return HomeWin;
            case 'D':
                return Draw;
            case 'W':
                return AwayWin;
            default:
                return AwayWin;
        }
    }

    public char toChar() {
        return this.value;
    }
}
