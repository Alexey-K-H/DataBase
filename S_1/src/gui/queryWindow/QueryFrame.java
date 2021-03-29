package gui.queryWindow;
import controllers.QueryController;
import javax.swing.*;

public abstract class QueryFrame extends JDialog {
    private final QueryController queryController;

    public QueryController getQueryController() {
        return queryController;
    }

    protected QueryFrame(QueryController queryController) {
        this.queryController = queryController;
    }
}
