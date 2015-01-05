package dk.mrspring.nfp;

/**
 * Created by MrSpring on 04-01-2015 for NoFireParticles.
 */
public class TransformEntry
{
    public TransformEntry(String minecraftVersion,String confirmedForgeVersion, String methodName, String methodDescription)
    {
        this.minecraftVersion = minecraftVersion;
        this.confirmedForgeVersion = confirmedForgeVersion;
        this.methodName = methodName;
        this.methodDescription = methodDescription;
    }

    String minecraftVersion;
    String confirmedForgeVersion;
    String methodName;
    String methodDescription;
}
