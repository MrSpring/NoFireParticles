package dk.mrspring.nfp;

import com.google.common.eventbus.EventBus;
import cpw.mods.fml.common.*;
import cpw.mods.fml.common.versioning.ArtifactVersion;
import cpw.mods.fml.common.versioning.DefaultArtifactVersion;
import cpw.mods.fml.common.versioning.VersionRange;

import java.io.File;
import java.security.cert.Certificate;
import java.util.*;

/**
 * Created by MrSpring on 30-11-2014 for No Fire Particles.
 */
public class NFPModContainer implements cpw.mods.fml.common.ModContainer
{
    private ModMetadata metadata;
    private ArtifactVersion processedVersion;

    public NFPModContainer()
    {
        metadata = new ModMetadata();
        metadata.modId = "nfp";
        metadata.name = "No Fire Particles";
        metadata.version = "1.0.0";
        metadata.credits = "Made by MrSpring, thanks to Arlyh for posting their ASM code publicly";
        metadata.authorList = Arrays.asList("MrSpring");
        metadata.description = "A simple mod that makes fire not emit fire particles. Plain and simple...";
        metadata.url = "http://mrspring.dk/mods/nfp";
        metadata.updateUrl = "http://mrspring.dk/mods/nfp/versions";
        metadata.screenshots = new String[0];
        metadata.logoFile = "";
    }

    @Override
    public void bindMetadata(MetadataCollection mc)
    {
    }

    @Override
    public List<ArtifactVersion> getDependants()
    {
        return Collections.emptyList();
    }

    @Override
    public List<ArtifactVersion> getDependencies()
    {
        return Collections.emptyList();
    }

    @Override
    public Set<ArtifactVersion> getRequirements()
    {
        return Collections.emptySet();
    }

    @Override
    public ModMetadata getMetadata()
    {
        return metadata;
    }

    @Override
    public Object getMod()
    {
        return null;
    }

    @Override
    public String getModId()
    {
        return metadata.modId;
    }

    @Override
    public String getName()
    {
        return metadata.name;
    }

    @Override
    public String getSortingRules()
    {
        return "";
    }

    @Override
    public File getSource()
    {
        return null;
    }

    @Override
    public String getVersion()
    {
        return metadata.version;
    }

    public boolean matches(Object mod)
    {
        return false;
    }

    @Override
    public void setEnabledState(boolean enabled)
    {
        System.out.println("enabled = [" + enabled + "]");
    }

    @Override
    public boolean registerBus(EventBus bus, LoadController controller)
    {
        return true;
    }

    @Override
    public ArtifactVersion getProcessedVersion()
    {
        if (processedVersion == null)
        {
            processedVersion = new DefaultArtifactVersion(getModId(), getVersion());
        }
        return processedVersion;
    }

    @Override
    public boolean isImmutable()
    {
        return false;
    }

    @Override
    public String getDisplayVersion()
    {
        return metadata.version;
    }

    @Override
    public VersionRange acceptableMinecraftVersionRange()
    {
        return Loader.instance().getMinecraftModContainer().getStaticVersionRange();
    }

    @Override
    public Certificate getSigningCertificate()
    {
        return null;
    }

    @Override
    public String toString()
    {
        return getModId();
    }

    @Override
    public Map<String, String> getCustomModProperties()
    {
        return EMPTY_PROPERTIES;
    }

    @Override
    public Class<?> getCustomResourcePackClass()
    {
        return null;
    }

    @Override
    public Map<String, String> getSharedModDescriptor()
    {
        return null;
    }

    @Override
    public Disableable canBeDisabled()
    {
        return null;
    }

    @Override
    public String getGuiClassName()
    {
        return null;
    }

    @Override
    public List<String> getOwnedPackages()
    {
        return null;
    }
}
