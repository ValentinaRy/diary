package diary.entry;

import java.time.LocalDateTime;
import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;

@Immutable
public abstract class Entry {
    @Nonnull private final LocalDateTime timeStamp;

    protected Entry(@Nonnull LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    @Nonnull
    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    @Nonnull
    public String printEntry() {
        return "timeStamp=" +timeStamp.toString();
    }
}
