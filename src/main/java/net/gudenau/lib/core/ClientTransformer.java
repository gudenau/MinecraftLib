package net.gudenau.lib.core;

import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.tree.*;
import org.objectweb.asm.*;
import static org.objectweb.asm.Opcodes.*;

/**
 * Created by gudenau on 12/26/2016.
 * <p>
 * lib
 */
public class ClientTransformer implements IClassTransformer {
    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass) {
        if("net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer".equals(name)){
            return transformTileEntityItemStackRenderer(basicClass, false);
        }else if("bqa".equals(name)){
            return transformTileEntityItemStackRenderer(basicClass, true);
        }

        return basicClass;
    }

    private byte[] transformTileEntityItemStackRenderer(byte[] basicClass, boolean obfuscated) {
        ClassReader classReader = new ClassReader(basicClass);
        ClassNode classNode = new ClassNode();
        classReader.accept(classNode, 0);

        String methodName = obfuscated ? "a" : "renderByItem";
        String methodDescription = obfuscated ? "(Lafi;)V" : "(Lnet/minecraft/item/ItemStack;)V";
        String itemName = obfuscated ? "afg" : "net/minecraft/item/Item";
        String itemStackName = obfuscated ? "afi" : "net/minecraft/item/ItemStack";

        for(MethodNode method : classNode.methods){
            if(methodName.equals(method.name) &&
                    methodDescription.equals(method.desc)){
                AbstractInsnNode startNode = null;
                AbstractInsnNode endNode = null;

                InsnList instructions = method.instructions;
                for(int i = 0; i < instructions.size(); i++){
                    AbstractInsnNode abstractInsnNode = instructions.get(i);
                    if(abstractInsnNode instanceof MethodInsnNode){
                        MethodInsnNode methodInsnNode = (MethodInsnNode) abstractInsnNode;
                        if("net/minecraftforge/client/ForgeHooksClient".equals(methodInsnNode.owner) &&
                                "renderTileItem".equals(methodInsnNode.name) &&
                                ("(L" + itemName + ";I)V").equals(methodInsnNode.desc) &&
                                INVOKESTATIC == methodInsnNode.getOpcode()){
                            endNode = abstractInsnNode;
                            break;
                        }
                    }
                }

                if(endNode == null){
                    System.err.printf("Unable to transform TileEntityItemStackRenderer, endNode is null!\n");
                    return null;
                }

                for(int i = instructions.indexOf(endNode); i > 0; i--){
                    AbstractInsnNode abstractInsnNode = instructions.get(i);
                    if(abstractInsnNode instanceof JumpInsnNode){
                        JumpInsnNode jumpInsnNode = (JumpInsnNode) abstractInsnNode;
                        if(IF_ACMPEQ == jumpInsnNode.getOpcode()){
                            startNode = abstractInsnNode;
                            break;
                        }
                    }
                }

                if(startNode == null){
                    System.err.printf("Unable to transform TileEntityItemStackRenderer, startNode is null!\n");
                    return null;
                }

                InsnList preInstructions = new InsnList();
                InsnList postInstructions = new InsnList();

                preInstructions.add(new VarInsnNode(ALOAD, 1));
                preInstructions.add(new MethodInsnNode(INVOKESTATIC, "net/gudenau/lib/core/ClientHooks", "preItemTileRender", "(L" + itemStackName + ";)V", false));

                postInstructions.add(new VarInsnNode(ALOAD, 1));
                postInstructions.add(new MethodInsnNode(INVOKESTATIC, "net/gudenau/lib/core/ClientHooks", "postItemTileRender", "(L" + itemStackName + ";)V", false));

                instructions.insert(startNode, preInstructions);
                instructions.insert(endNode, postInstructions);

                break;
            }
        }

        ClassWriter classWriter = new ClassWriter(0);
        classNode.accept(classWriter);
        return classWriter.toByteArray();
    }
}
