package nn.iamj.borne.modules.commerce;

import nn.iamj.borne.modules.commerce.page.CommercePage;

public interface Commerce {

    void addPage(final CommercePage page);

    void removePage(final CommercePage page);

    void clearPages();

}
