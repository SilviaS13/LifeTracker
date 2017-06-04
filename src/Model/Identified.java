package Model;

import java.util.UUID;

public class Identified {
    public Long Id;

    public void GenerateId()
    {
        Id = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
    }
}
