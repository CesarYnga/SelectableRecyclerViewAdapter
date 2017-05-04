package com.cesarynga.selectablerecyclerview.adapter;

import android.support.annotation.IntDef;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.Checkable;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Adapter that helps to select items in {@link RecyclerView}
 */
public abstract class SelectableAdapter<VH extends SelectableAdapter.ViewHolder> extends RecyclerView.Adapter<VH> {

    /**
     * Normal list that does not indicate choices
     */
    public static final int CHOICE_MODE_NONE = 0;

    /**
     * The list allows up to one choice
     */
    public static final int CHOICE_MODE_SINGLE = 1;

    /**
     * The list allows multiple choices
     */
    public static final int CHOICE_MODE_MULTIPLE = 2;


    private RecyclerView recyclerView;
    private int choiceMode = CHOICE_MODE_NONE;
    private SparseBooleanArray checkStates = new SparseBooleanArray();

    private int checkedItemCount = 0;

    public SelectableAdapter(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({CHOICE_MODE_NONE, CHOICE_MODE_SINGLE, CHOICE_MODE_MULTIPLE})
    public @interface ChoiceMode {
    }

    /**
     * Defines the choice behavior for the List. By default, Lists do not have any choice behavior
     * ({@link #CHOICE_MODE_NONE}). By setting the choiceMode to {@link #CHOICE_MODE_SINGLE}, the
     * List allows up to one item to  be in a chosen state. By setting the choiceMode to
     * {@link #CHOICE_MODE_MULTIPLE}, the list allows any number of items to be chosen.
     *
     * @param choiceMode One of {@link #CHOICE_MODE_NONE}, {@link #CHOICE_MODE_SINGLE}, or
     *                   {@link #CHOICE_MODE_MULTIPLE}
     */
    public void setChoiceMode(@ChoiceMode int choiceMode) {
        this.choiceMode = choiceMode;
        if (this.choiceMode != CHOICE_MODE_NONE) {
            if (this.checkStates == null) {
                this.checkStates = new SparseBooleanArray(0);
            }
        }
    }

    public int getCheckedItemCount() {
        return checkedItemCount;
    }

    private void setCheckStateInView(View view, boolean checked) {
        if (view instanceof Checkable) {
            ((Checkable) view).setChecked(checked);
        } else {
            view.setActivated(checked);
        }
    }

    /**
     * ViewHolder that works with {@link SelectableAdapter}
     * If your adapter is a subclass of {@link SelectableAdapter}, you must use a subclass
     * of {@link ViewHolder}
     */
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private OnItemClickListener onItemClickListener;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }

        /**
         * Method for handling item click listener. Using this method it will perform the click action
         * and keep the selection behavior according to the choice mode.
         *
         * @param listener listener to be call when user click an item
         */
        protected void setOnItemClickListener(OnItemClickListener listener) {
            this.onItemClickListener = listener;
        }

        @Override
        public void onClick(View v) {
            performItemClick();
            this.onItemClickListener.onItemClick();
        }

        private void performItemClick() {
            if (choiceMode != CHOICE_MODE_NONE) {
                final int previousPosition = checkStates.keyAt(0);
                final int currentPosition = getAdapterPosition();
                if (choiceMode == CHOICE_MODE_SINGLE) {
                    final boolean checked = !checkStates.get(currentPosition, false);
                    if (checked) {
                        checkStates.clear();
                        checkStates.put(currentPosition, true);
                        checkedItemCount = 1;
                        setCheckStateInView(
                                recyclerView.findViewHolderForAdapterPosition(previousPosition).itemView,
                                false);
                        setCheckStateInView(itemView, true);
                    } else if (checkStates.size() == 0 || !checkStates.valueAt(0)) {
                        checkedItemCount = 0;
                    }
                } else if (choiceMode == CHOICE_MODE_MULTIPLE) {
                    final int position = getAdapterPosition();
                    boolean checked = !checkStates.get(position, false);
                    checkStates.put(position, checked);
                    if (checked) {
                        checkedItemCount++;
                    } else {
                        checkedItemCount--;
                    }
                    setCheckStateInView(itemView, checked);
                }
            }
        }
    }

    public interface OnItemClickListener {
        void onItemClick();
    }
}
