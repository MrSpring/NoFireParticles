package dk.mrspring.nfp;

import com.google.gson.Gson;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin;
import net.minecraft.crash.CrashReport;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * Created by MrSpring on 30-11-2014 for No Fire Particles.
 */
public class NFPFMLLoadingPlugin implements IFMLLoadingPlugin
{
    public static TransformEntry transformEntry;
    public static TransformList transformList;

    @Override
    public String[] getASMTransformerClass()
    {
        File file = new File("nfp.json");
        try
        {
            FileUtils.copyURLToFile(new URL("http://mrspring.dk/mods/nfp/transforms.json"), file);
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        try
        {
            transformList = this.readFromFile(file);
            transformEntry = this.getCurrentEntryFromLoader(transformList);
        } catch (IOException e)
        {
            e.printStackTrace();
        }

        return new String[]{NFPClassTransformer.class.getName()};
    }

    public TransformList readFromFile(File file) throws IOException
    {
        Gson gson = new Gson();
        String jsonCode = FileUtils.readFileToString(file);
        System.out.println("Read from nfp.json: ");
        System.out.println(jsonCode);
        return gson.fromJson(jsonCode, TransformList.class);
    }

    public static TransformEntry getCurrentEntryFromLoader(TransformList list)
    {
        String currentMinecraftVersion = "";
        try
        {
            Field mcVersion = Loader.class.getField("MC_VERSION");
            currentMinecraftVersion = (String) mcVersion.get(null);
        } catch (NoSuchFieldException e)
        {
            e.printStackTrace();

        } catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }
        for (TransformEntry entry : list.transformList)
        {
            System.out.println("entry.minecraftVersion = " + entry.minecraftVersion);
            System.out.println("entry.methodName = " + entry.methodName);
            System.out.println("entry.methodDescription = " + entry.methodDescription);
            System.out.println("currentMinecraftVersion = " + currentMinecraftVersion);
            if (entry.minecraftVersion.equals(currentMinecraftVersion))
                return entry;
        }
        return null;
    }

    public static TransformEntry getCurrentEntryFromCallableMinecraftVersion(TransformList list)
    {
        String currentMinecraftVersion = "";
        try
        {
            Class aClass = Class.forName("net.minecraft.crash.CallableMinecraftVersion");
            Constructor aConstructor = aClass.getConstructor(CrashReport.class);
            Object callableMinecraftVersion = aConstructor.newInstance(null);
            currentMinecraftVersion = (String) ((Callable) callableMinecraftVersion).call();
        } catch (Exception e1)
        {
            e1.printStackTrace();
        }
        for (TransformEntry entry : list.transformList)
        {
            System.out.println("entry.minecraftVersion = " + entry.minecraftVersion);
            System.out.println("entry.methodName = " + entry.methodName);
            System.out.println("entry.methodDescription = " + entry.methodDescription);
            System.out.println("currentMinecraftVersion = " + currentMinecraftVersion);
            if (entry.minecraftVersion.equals(currentMinecraftVersion))
                return entry;
        }
        return null;
    }

    @Override
    public String getModContainerClass()
    {
        return "dk.mrspring.nfp.NFPModContainer";
    }

    @Override
    public String getSetupClass()
    {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data)
    {

    }

    @Override
    public String getAccessTransformerClass()
    {
        return null;
    }
}
