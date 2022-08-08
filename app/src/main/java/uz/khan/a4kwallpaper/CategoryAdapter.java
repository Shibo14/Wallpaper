package uz.khan.a4kwallpaper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    ArrayList<CategoryModel> categoryModelArrayList;
    Context context;
    CategoryClickInterface categoryClickInterface;

    public CategoryAdapter(ArrayList<CategoryModel> categoryModelArrayList, Context context, CategoryClickInterface categoryClickInterface) {
        this.categoryModelArrayList = categoryModelArrayList;
        this.context = context;
        this.categoryClickInterface = categoryClickInterface;
    }

//    public CategoryAdapter(ArrayList<CategoryModel> categoryModelArrayList, Context context) {
//        this.categoryModelArrayList = categoryModelArrayList;
//        this.context = context;
//    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.category_item, parent, false);

        return new CategoryAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        CategoryModel categoryModel = categoryModelArrayList.get(position);
        holder.categoryTv.setText(categoryModel.getCategoryImg());
        Glide.with(context).load(categoryModel.getCategoryImgUrl()).into(holder.categoryImg);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                categoryClickInterface.onCategoryClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView categoryTv;
        ImageView categoryImg;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryImg = itemView.findViewById(R.id.idiVICategory);
            categoryTv = itemView.findViewById(R.id.idtVICategory);
        }
    }
}

interface CategoryClickInterface {
    void onCategoryClick(int position);
}
