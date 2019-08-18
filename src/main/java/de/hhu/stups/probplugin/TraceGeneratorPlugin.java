package de.hhu.stups.probplugin;

import de.prob2.ui.plugin.MenuEnum;
import de.prob2.ui.plugin.ProBPlugin;
import de.prob2.ui.plugin.ProBPluginHelper;
import de.prob2.ui.plugin.ProBPluginManager;
import javafx.scene.control.MenuItem;
import org.pf4j.PluginWrapper;

/**
 * Created by fabian on 17.11.18.
 */
public class TraceGeneratorPlugin extends ProBPlugin {

    private MenuItem menuItem;

    public TraceGeneratorPlugin(PluginWrapper wrapper, ProBPluginManager manager, ProBPluginHelper helper) {
        super(wrapper, manager, helper);
    }

    @Override
    public String getName() {
        return "Trace Generator";
    }

    @Override
    public void startPlugin() throws Exception {
        createMenu();
    }

    @Override
    public void stopPlugin() throws Exception {
        getProBPluginHelper().removeMenuItem(MenuEnum.ADVANCED_MENU, menuItem);
    }

    private void createMenu() {
        menuItem = new MenuItem("Trace Generator");
        getProBPluginHelper().addMenuItem(MenuEnum.ADVANCED_MENU, 3, menuItem);
        menuItem.setOnAction(e -> getInjector().getInstance(TraceGeneratorStage.class).show());
    }
}
