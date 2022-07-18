package adventure.pkg.items;

import adventure.pkg.*;

public class Potion extends Item{

    // Potion attributes.
    private final String type;

    public Potion(String name, int rarity, String type) {
        super(name, rarity);
        this.type = type;
    }

    public void use(Hero hero){
        switch(this.type){
            case "heal" -> {
                hero.setHP(Math.min((hero.getHP() + 5), hero.getMaxHealth()));
                System.out.println(hero.getName() + " heals! HP: " + (hero.getHP() - 5) + " -> " + hero.getHP());
            }

            case "atkBoost" -> hero.setAtk(hero.getAtk()+1);

            case "defBoost" -> hero.setDef(hero.getDef()+1);

            case "hpBoost" -> {
                hero.setMaxHealth(hero.getMaxHealth()+2);
                hero.setHP(hero.getHP()+2);
            }
        }
    }

    public void info(){
        switch(this.type){
            case "heal" -> System.out.println("This potion heals you for 5 hit points.");

            case "atkBoost" -> System.out.println("This potion permanently boosts your attack stat by 1.");

            case "defBoost" -> System.out.println("This potion permanently boosts your defence stat by 1.");

            case "hpBoost" -> System.out.println("This potion permanently boosts your health stat by 2.");

        }
    }

}
