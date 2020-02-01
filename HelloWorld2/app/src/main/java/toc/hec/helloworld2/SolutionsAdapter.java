//https://guides.codepath.com/android/using-the-recyclerview

package toc.hec.helloworld2;



import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;

// Create the basic adapter extending from RecyclerView.Adapter
// Note that we specify the custom ViewHolder which gives us access to our views
public  class SolutionsAdapter extends
        RecyclerView.Adapter<SolutionsAdapter.ViewHolder> {

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView nameTextView;


        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            nameTextView = (TextView) itemView.findViewById(R.id.solution_name);

        }
    }

    // Store a member variable for the solutions
    private List<Solution> mSolutions;

    // Pass in the solution array into the constructor
    public SolutionsAdapter(List<Solution> solutions) {
        mSolutions = solutions;
    }

    // Usually involves inflating a layout from XML and returning the holder
    @Override
    public SolutionsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View solutionView = inflater.inflate(R.layout.item_solution, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(solutionView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(SolutionsAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        Solution solution = mSolutions.get(position);

        // Set item views based on your views and data model
        TextView textView = viewHolder.nameTextView;
        textView.setText(solution.getName());

    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return mSolutions.size();
    }


}
