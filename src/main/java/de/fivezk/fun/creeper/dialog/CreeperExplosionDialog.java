package de.fivezk.fun.creeper.dialog;

import de.fivezk.fun.config.FunConfig;
import io.papermc.paper.dialog.Dialog;
import io.papermc.paper.registry.data.dialog.ActionButton;
import io.papermc.paper.registry.data.dialog.DialogBase;
import io.papermc.paper.registry.data.dialog.action.DialogAction;
import io.papermc.paper.registry.data.dialog.body.DialogBody;
import io.papermc.paper.registry.data.dialog.type.DialogType;
import net.kyori.adventure.key.Key;

import java.util.List;

public final class CreeperExplosionDialog {

    public static final Key EXPLODE_KEY = Key.key("fun:creeper_explode");
    public static final Key DENY_KEY = Key.key("fun:creeper_deny");
    private final FunConfig config;

    public CreeperExplosionDialog(FunConfig config) {
        this.config = config;
    }

    public Dialog create() {
        ActionButton explodeButton = ActionButton.create(
                config.message("creeper-dialog.buttons.explode.text"),
                config.message("creeper-dialog.buttons.explode.tooltip"),
                config.integer("creeper-dialog.buttons.explode.width", 180),
                DialogAction.customClick(EXPLODE_KEY, null)
        );

        ActionButton denyButton = ActionButton.create(
                config.message("creeper-dialog.buttons.deny.text"),
                config.message("creeper-dialog.buttons.deny.tooltip"),
                config.integer("creeper-dialog.buttons.deny.width", 180),
                DialogAction.customClick(DENY_KEY, null)
        );

        return Dialog.create(builder -> builder.empty()
                .base(DialogBase.create(
                        config.message("creeper-dialog.title"),
                        config.message("creeper-dialog.external-title"),
                        false,
                        false,
                        DialogBase.DialogAfterAction.CLOSE,
                        List.of(DialogBody.plainMessage(config.message("creeper-dialog.body"))),
                        List.of()
                ))
                .type(DialogType.multiAction(List.of(explodeButton, denyButton), null, 1))
        );
    }
}
