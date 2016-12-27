package net.gudenau.lib;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import net.minecraftforge.fml.common.DummyModContainer;
import net.minecraftforge.fml.common.LoadController;
import net.minecraftforge.fml.common.ModMetadata;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collections;

/**
 * Created by gudenau on 12/26/2016.
 * <p>
 * lib
 */
public class GudLib extends DummyModContainer {
    public GudLib(){
        super(new ModMetadata());

        ModMetadata modMetadata = getMetadata();
        modMetadata.authorList = Collections.singletonList("gudenau");
        modMetadata.description = "A library mod by gudenau";
        modMetadata.modId = "gud_lib";
        modMetadata.name = "GudLib";
        modMetadata.version = "1.0.0.0";
    }

    @Override
    public boolean registerBus(EventBus bus, LoadController controller){
        bus.register(this);
        return true;
    }

    @Subscribe
    public void preInit(FMLPreInitializationEvent event){
        Logger logger = LogManager.getLogger("gud_lib");
        logger.info("preInit");
    }
}
