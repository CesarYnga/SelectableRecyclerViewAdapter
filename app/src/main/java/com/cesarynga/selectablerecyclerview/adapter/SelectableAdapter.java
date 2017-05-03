package com.cesarynga.selectablerecyclerview.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.Checkable;

public abstract class SelectableAdapter<VH extends SelectableAdapter.ViewHolder> extends RecyclerView.Adapter<VH> {

    public static final int CHOICE_MODE_NONE = 0;
    public static final int CHOICE_MODE_SINGLE = 1;
    public static final int CHOICE_MODE_MULTIPLE = 2;


    private RecyclerView recyclerView;
    private int choiceMode = CHOICE_MODE_NONE;
    private SparseBooleanArray checkStates = new SparseBooleanArray();

    private int checkedItemCount = 0;

    public SelectableAdapter(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }

    public void setChoiceMode(int choiceMode) {
        this.choiceMode = choiceMode;
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

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
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
}
