package nn.iamj.borne.modules.commerce;

import lombok.Getter;
import lombok.Setter;
import nn.iamj.borne.modules.commerce.page.CommercePage;

import java.util.List;

@Getter @Setter
public class Commerce {

    private List<CommercePage> pageList;

    public void addPage(final CommercePage page) {
        this.pageList.add(page);
    }

    public void removePage(final CommercePage page) {
        this.pageList.remove(page);
    }

    public void clearPages() {
        this.pageList.clear();
    }

}
