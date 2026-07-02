package de.fivezk.fun.creeper.dialog;

import io.papermc.paper.dialog.Dialog;
import io.papermc.paper.registry.data.dialog.ActionButton;
import io.papermc.paper.registry.data.dialog.DialogBase;
import io.papermc.paper.registry.data.dialog.action.DialogAction;
import io.papermc.paper.registry.data.dialog.body.DialogBody;
import io.papermc.paper.registry.data.dialog.type.DialogType;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

import java.util.List;

public final class CreeperExplosionDialog {

    public static final Key EXPLODE_KEY = Key.key("fun:creeper_explode");
    public static final Key DENY_KEY = Key.key("fun:creeper_deny");

    public Dialog create() {
        ActionButton explodeButton = ActionButton.create(
                Component.text("Ja, explodieren.", NamedTextColor.GREEN),
                Component.text("Der Creeper darf explodieren."),
                180,
                DialogAction.customClick(EXPLODE_KEY, null)
        );

        ActionButton denyButton = ActionButton.create(
                Component.text("Nein, gerade nicht.", NamedTextColor.RED),
                Component.text("Der Creeper darf nicht explodieren."),
                180,
                DialogAction.customClick(DENY_KEY, null)
        );

        return Dialog.create(builder -> builder.empty()
                .base(DialogBase.create(
                        Component.text("Creeper Explosion", NamedTextColor.DARK_GREEN),
                        Component.text("Creeper Explosion"),
                        false,
                        false,
                        DialogBase.DialogAfterAction.CLOSE,
                        List.of(DialogBody.plainMessage(Component.text("Ein Creeper möchte bei dir explodieren. Was soll passieren?"))),
                        List.of()
                ))
                .type(DialogType.multiAction(List.of(explodeButton, denyButton), null, 1))
        );
    }
}
