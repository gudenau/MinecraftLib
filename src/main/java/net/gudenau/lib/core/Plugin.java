package net.gudenau.lib.core;

import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;

import java.util.Map;

/**
 * Created by gudenau on 12/26/2016.
 * <p>
 * lib
 */
public class Plugin implements IFMLLoadingPlugin {
    @Override
    public String[] getASMTransformerClass() {
        return new String[]{
                "net.gudenau.lib.core.ClientTransformer"
        };
    }

    @Override
    public String getModContainerClass() {
        return "net.gudenau.lib.GudLib";
    }

    @Override
    public String getSetupClass() {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data) {}

    @Override
    public String getAccessTransformerClass() { return null; }
}
