# Selectable RecyclerView Adapter
This is an example of performing selection in a RecyclerView.

## Usage
1. Create your own adapter for your RecyclerView extending SelectableAdapter.
Also, its ViewHolder must extend SelectableAdapter.ViewHolder.

```java
public class MyAdapter extends SelectableAdapter<MyAdapter.ViewHolder> {

    ...
    
    public MyAdapter(RecyclerView recyclerView, List<String> list) {
            super(recyclerView);
            this.list = list;
    }
    ...
    
    public class ViewHolder extends SelectableAdapter.ViewHolder {
        
        ...
        
        public ViewHolder(View itemView) {
            super(itemView);
            ...        
        }
        ...
    }
}
```

2. Set a choice mode in the adapter. Could be `CHOICE_MODE_NONE` (default), `CHOICE_MODE_SINGLE` or `CHOICE_MODE_MULTIPLE`.
```java
myAdapter.setChoiceMode(SelectableAdapter.CHOICE_MODE_SINGLE);
```

3. If you want to use your own item click listener, you should use `SelectableAdapter.ViewHolder.setOnItemClickListener` in your ViewHolder.
```java
public class ViewHolder extends SelectableAdapter.ViewHolder {
    ...
    public ViewHolder(View itemView) {
        super(itemView);
        ...
        setOnItemClickListener(new OnItemClickListener() {
                        @Override
                        public void onItemClick() {
                            // Your code
                        }
        }
    }
}
```