package dk.mrspring.nfp;

import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.*;

/**
 * Created by MrSpring on 30-11-2014 for No Fire Particles.
 */
public class NFPClassTransformer implements IClassTransformer
{
    boolean print = true, foundCallable = false, hasCalled = false;

    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass)
    {
        if (print)
            System.out.println("[NFP] Name: " + name + ", transformed name: " + transformedName);
        if (!hasCalled&&foundCallable)
        {
            NFPFMLLoadingPlugin.foundCallableMinecraftVersion();
            hasCalled = true;
        }

        if (name.equals("bfr/a") || name.equals("alb") || transformedName.equals("net.minecraft.block.BlockFire"))
        {
            System.out.println("[NFP] Found BlockFire!");
            print = false;

            basicClass = this.deleteMethod(basicClass);
        }else if (name.equals("net.minecraft.crash.CallableMinecraftVersion"))
        {
            foundCallable = true;
            return basicClass;
        }

        return basicClass;
    }

    public byte[] deleteMethod(byte[] bytes)
    {
        ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
        NFPClassVisitor visitor = new NFPClassVisitor(Opcodes.ASM4, writer);
        ClassReader reader = new ClassReader(bytes);
        reader.accept(visitor, 0);
        return writer.toByteArray();
    }

    class NFPClassVisitor extends ClassVisitor
    {
        private String methodName;
        private String methodDesc;

        public NFPClassVisitor(int api, ClassVisitor cv)
        {
            super(api, cv);
            if (NFPFMLLoadingPlugin.transformEntry != null)
            {
                this.methodName = NFPFMLLoadingPlugin.transformEntry.methodName;
                this.methodDesc = NFPFMLLoadingPlugin.transformEntry.methodDescription;
            }
        }

        @Override
        public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions)
        {
            System.out.println("access = [" + access + "], name = [" + name + "], desc = [" + desc + "], signature = [" + signature + "], exceptions = [" + exceptions + "], looking for: name = [" + this.methodName + "], desc = [" + this.methodDesc + "]");

            if (NFPFMLLoadingPlugin.transformEntry != null)
            {
                for (TransformEntry entry:NFPFMLLoadingPlugin.transformList.transformList)
                    if (name.equals(entry.methodName) && desc.equals(entry.methodDescription))
                    {
                        System.out.println("Found " + name + " with desc " + desc);
                        return null;
                    }
            }
            return super.visitMethod(access, name, desc, signature, exceptions);
        }
    }
}
