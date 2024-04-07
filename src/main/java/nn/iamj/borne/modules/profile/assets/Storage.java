package nn.iamj.borne.modules.profile.assets;

import lombok.Getter;
import lombok.Setter;
import nn.iamj.borne.modules.skill.SkillType;
import nn.iamj.borne.modules.talent.TalentType;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Getter
public final class Storage {

    @Setter
    private int energyCrystals;

    @Setter
    private int commonBossMoney;
    @Setter
    private int rareBossMoney;
    @Setter
    private int epicBossMoney;
    @Setter
    private int legendBossMoney;

    private final Map<TalentType, Integer> talents = new ConcurrentHashMap<>();
    private final Map<SkillType, Integer> skills = new ConcurrentHashMap<>();

    public void addEnergyCrystals(final int number) {
        this.energyCrystals += number;
    }

    public boolean removeEnergyCrystals(final int number) {
        if (this.energyCrystals - number < 0)
            return false;

        this.energyCrystals = this.energyCrystals - number;

        return true;
    }

    public void addCommonBossMoney() {
        this.addCommonBossMoney(1);
    }

    public void addCommonBossMoney(final int number) {
        this.commonBossMoney += number;
    }

    public boolean removeCommonBossMoney() {
        return removeCommonBossMoney(1);
    }

    public boolean removeCommonBossMoney(final int number) {
        if (this.commonBossMoney - number < 0)
            return false;

        this.commonBossMoney = this.commonBossMoney - number;

        return true;
    }

    public void addRareBossMoney() {
        this.addRareBossMoney(1);
    }

    public void addRareBossMoney(final int number) {
        this.rareBossMoney += number;
    }

    public boolean removeRareBossMoney() {
        return removeRareBossMoney(1);
    }

    public boolean removeRareBossMoney(final int number) {
        if (this.rareBossMoney - number < 0)
            return false;

        this.rareBossMoney = this.rareBossMoney - number;

        return true;
    }

    public void addEpicBossMoney() {
        this.addEpicBossMoney(1);
    }

    public void addEpicBossMoney(final int number) {
        this.epicBossMoney += number;
    }

    public boolean removeEpicBossMoney() {
        return removeEpicBossMoney(1);
    }

    public boolean removeEpicBossMoney(final int number) {
        if (this.epicBossMoney - number < 0)
            return false;

        this.epicBossMoney = this.epicBossMoney - number;

        return true;
    }

    public void addLegendBossMoney() {
        this.addLegendBossMoney(1);
    }

    public void addLegendBossMoney(final int number) {
        this.legendBossMoney += number;
    }

    public boolean removeLegendBossMoney() {
        return removeLegendBossMoney(1);
    }

    public boolean removeLegendBossMoney(final int number) {
        if (this.legendBossMoney - number < 0)
            return false;

        this.legendBossMoney = this.legendBossMoney - number;

        return true;
    }

    public void upgradeTalent(final TalentType type) {
        this.upgradeTalent(type, 1);
    }

    public void upgradeTalent(final TalentType type, final int number) {
        if (number < 0)
            return;

        this.talents.put(type, getTalent(type) + number);
    }

    public void downgradeTalent(final TalentType type, final int number) {
        if (number < 0)
            return;

        this.talents.put(type, getTalent(type) > number ? getTalent(type) - number : 0);
    }

    public boolean resetTalent(final TalentType type) {
        if (!this.talents.containsKey(type)) return false;

        this.talents.remove(type);
        return true;
    }

    public int getTalent(final TalentType type) {
        return this.talents.getOrDefault(type, 0);
    }

    public void upgradeSkill(final SkillType type) {
        this.upgradeSkill(type, 1);
    }

    public void upgradeSkill(final SkillType type, final int number) {
        if (number < 0)
            return;

        this.skills.put(type, getSkill(type) + number);
    }

    public void downgradeSkill(final SkillType type, final int number) {
        if (number < 0)
            return;

        this.skills.put(type, getSkill(type) > number ? getSkill(type) - number : 0);
    }

    public boolean resetSkill(final SkillType type) {
        if (!this.skills.containsKey(type)) return false;

        this.skills.remove(type);
        return true;
    }

    public int getSkill(final SkillType type) {
        return this.skills.getOrDefault(type, 0);
    }

}
