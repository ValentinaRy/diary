package diary.entry;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;
import java.time.LocalDateTime;

@Immutable
public class IntegerEntry extends Entry {
    private final int value;

    public IntegerEntry(@Nonnull LocalDateTime timeStamp, int value) {
        super(timeStamp);
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Nonnull
    @Override
    public EntryType getEntryType() {
        return EntryType.INTEGER;
    }

    @Override
    @Nonnull
    public String printEntry() {
        return super.printEntry() + ", intValue=" + value;
    }
}
