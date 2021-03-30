package diary.entry;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;
import java.time.LocalDateTime;

@Immutable
public class StringEntry extends Entry {
    @Nonnull private final String value;

    public StringEntry(@Nonnull LocalDateTime timeStamp, @Nonnull String value) {
        super(timeStamp);
        this.value = value;
    }

    @Nonnull
    public String getValue() {
        return value;
    }

    @Nonnull
    @Override
    public EntryType getEntryType() {
        return EntryType.STRING;
    }

    @Override
    @Nonnull
    public String printEntry() {
        return super.printEntry() + ", strValue=" + value;
    }
}
