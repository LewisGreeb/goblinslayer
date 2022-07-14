package adventure.pkg.items;

import adventure.pkg.*;

public class Potion extends Item{

    // Potion attributes.
    private String type;

    public Potion(String name, int rarity, String type) {
        super(name, rarity);
        this.type = type;
    }

    public void use(Hero hero){
        switch(this.type){
            case "heal" -> hero.setHP(Math.min((hero.getHP() + 5), hero.getMaxHealth()));

            case "atkBoost" -> hero.setAtk(hero.getAtk()+1);

            case "defBoost" -> hero.setDef(hero.getDef()+1);

            case "hpBoost" -> {
                hero.setMaxHealth(hero.getMaxHealth()+2);
                hero.setHP(hero.getHP()+2);
            }
        }
    }

}
