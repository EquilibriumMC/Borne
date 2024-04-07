package nn.iamj.borne.modules.boss.berserk;

import lombok.Getter;
import nn.iamj.borne.modules.api.events.boss.BossBerserkEvent;
import nn.iamj.borne.modules.boss.Boss;
import nn.iamj.borne.modules.util.event.EventUtils;

@Getter
public abstract class BossBerserk {

    private final Boss boss;

    private boolean active;

    public BossBerserk(final Boss boss) {
        this.boss = boss;
    }

    public final void tick() {
        if (this.active)
            this.stop();
        else this.start();
    }

    public void start() {
        EventUtils.callStaticEvent(new BossBerserkEvent(this.boss, BossBerserkEvent.Type.START));

        this.active = true;
    }

    public void stop() {
        EventUtils.callStaticEvent(new BossBerserkEvent(this.boss, BossBerserkEvent.Type.END));

        this.active = false;
    }

}
