from pathlib import Path

BASE = Path(__file__).parent / "base"
ITEMS = Path(__file__).parent.parent / "main" / "java" / "net" / "ak1ve" / "create_spawn_eggs" / "items"
ASSETS = Path(__file__).parent.parent / "main" / "resources" / "assets" / "create_spawn_eggs"

template_mechanical = \
"""
\t\tobjects.add(items.register(
                "{0}_mechanical_spawn_egg",
                () -> new FilledMechanicalSpawnEggItem(EntityType.{1}, new Item.Properties())
        ));
"""

template_canister = \
"""
\t\tobjects.add(items.register(
                "{0}_soul_canister",
                () -> new SoulCanister(EntityType.{1}, new Item.Properties())
        ));
"""

template_lang = '\t"item.create_spawn_eggs.{0}_mechanical_spawn_egg": "Mechanical {1} Spawn Egg",\n\t"item.create_spawn_eggs.{0}_soul_canister": "{1} Soul Canister"'

eggs = Path(__file__).parent / "eggs.txt"



def spawn_eggs():
    with eggs.open("r") as f:
        return [x.strip() for x in f.read().split("\n")]

def title_case(x):
    return x.replace("_", " ").title()

def mechanical_registry():
    return [template_mechanical.format(x.lower(), x) for x in spawn_eggs()]

def canister_registry():
    return [template_canister.format(x.lower(), x) for x in spawn_eggs()]

def item_lang():
    return [template_lang.format(x.lower(), title_case(x)) for x in spawn_eggs()]


def generate_filled_spawn_eggs():
    templated_item_file = BASE / "GenerateRegisterSpawnEggs.java"
    target_item_file = ITEMS / "GeneratedRegisterSpawnEggs.java"

    with templated_item_file.open("r") as template:
        with target_item_file.open("w") as target:
            target.write(template.read().format(lines="\n".join(mechanical_registry())))

def generate_canister_spawn_eggs():
    templated_item_file = BASE / "GenerateRegisterSoulCanisters.java"
    target_item_file = ITEMS / "GeneratedRegisterSoulCanisters.java"

    with templated_item_file.open("r") as template:
        with target_item_file.open("w") as target:
            target.write(template.read().format(lines="\n".join(canister_registry())))

def generate_lang():
    templated_lang_file = BASE / "en_us.json"
    target_lang_file = ASSETS / "lang" / "en_us.json"
    with templated_lang_file.open("r") as template:
        with target_lang_file.open("w") as target:
            target.write(template.read().format(lines=",\n".join(item_lang())))

def generate_mechanical_models():
    templated_model_file = BASE / "mechanical_model.json"
    with templated_model_file.open("r") as template:
        temp = template.read()
        for egg in spawn_eggs():
            path = ASSETS / "models" / "item" / (egg.lower() + "_mechanical_spawn_egg.json")
            with path.open("w") as target:
                target.write(temp)

def generate_canister_models():
    templated_model_file = BASE / "canister_model.json"
    with templated_model_file.open("r") as template:
        temp = template.read()
        for egg in spawn_eggs():
            path = ASSETS / "models" / "item" / (egg.lower() + "_soul_canister.json")
            with path.open("w") as target:
                target.write(temp)

def main():
    generate_filled_spawn_eggs()
    generate_lang()
    generate_mechanical_models()
    generate_canister_models()
    generate_canister_spawn_eggs()


if __name__ == "__main__":
    main()