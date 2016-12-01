package mail.ru.githubclient.ui;

import android.support.v7.widget.RecyclerView;
import android.view.View;

public class DefaultHolder<D, T extends View & BindableView<D>> extends RecyclerView.ViewHolder {
    public DefaultHolder(T itemView) {
        super(itemView);
    }

    @SuppressWarnings("unchecked")
    public void bind(D data) {
        ((T) itemView).bind(data);
    }
}
