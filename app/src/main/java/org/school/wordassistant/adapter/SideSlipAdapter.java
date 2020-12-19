package org.school.wordassistant.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.mcxtzhang.swipemenulib.SwipeMenuLayout;

import org.school.wordassistant.R;
import org.school.wordassistant.entity.Word;

import java.util.List;

public class SideSlipAdapter extends BaseAdapter{

    private LayoutInflater inflater;
    private List<Word> list;
    private Context context;

    public SideSlipAdapter(Context context, List<Word> list) {
        this.inflater = LayoutInflater.from(context);
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;

        View closeView = null;

        if (convertView == null){  //表示对应的view是空的,此时需要重新创建
            //表示加载对应的显示item元素
            convertView = inflater.inflate(R.layout.listview_item_delete, parent, false);
            holder = new ViewHolder();
            holder.content = convertView.findViewById(R.id.content);
            holder.btn_collect = convertView.findViewById(R.id.btnTop);
            holder.btn_delete = convertView.findViewById(R.id.btnDelete);
            holder.btn_addColor = convertView.findViewById(R.id.btnAddColor);
            convertView.setTag(holder);
        }


        if (closeView == null){  //表示判断closeView是不是空的,如果是那么就将convertView赋值过去
            closeView = convertView;
        }


        final View finalCloseView = closeView;  // listView的itemView

        //得到的是对应的原来的holder对象
        holder = (ViewHolder) convertView.getTag();

        //向listview组件item中对应设置数据
        holder.content.setText(list.get(position).getWordString());


        /**
         * 收藏按钮的单击事件
         * **/
        holder.btn_collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "已收藏,请前往生词本查看", Toast.LENGTH_SHORT).show();

                if (delItemListener != null){  //显示判断监听器接口是不是空的,如果不是的直接调用接口中的删除方法
                    delItemListener.collect(position); // 调用接口的方法，回调删除该项数据(是按照list中的index下标删除数据)
                }
                ((SwipeMenuLayout)(finalCloseView)).quickClose(); //关闭侧滑菜单：需要将itemView强转，然后调用quickClose()方法
            }
        });


        // 删除按钮的单击事件
        holder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "已删除,请前往熟词本查看", Toast.LENGTH_SHORT).show();
                if (delItemListener != null){  //显示判断监听器接口是不是空的,如果不是的直接调用接口中的删除方法
                    delItemListener.delete(position); // 调用接口的方法，回调删除该项数据(是按照list中的index下标删除数据)
                }
                ((SwipeMenuLayout)(finalCloseView)).quickClose();// 关闭侧滑菜单

            }
        });



        /**
         * 增加记忆程度的单击事件
         * **/
        holder.btn_addColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (delItemListener != null){  //显示判断监听器接口是不是空的,如果不是的直接调用接口中的删除方法
                    delItemListener.addColor(position); // 调用接口的方法，回调删除该项数据(是按照list中的index下标删除数据)
                }

                ((SwipeMenuLayout)(finalCloseView)).quickClose();// 关闭侧滑菜单

            }
        });

        return convertView;  //返回对应的item组件适配之后的view
    }


    /**
     * 缓存控件用
     */
    static class ViewHolder{
        TextView content;// 展示内容
        Button btn_collect;// 收藏
        Button btn_delete;// 删除
        Button btn_addColor;  //加深标记
    }


    // 定义接口，包含了对于数据操作的方法
    public interface DeleteItem{
        //删除数据的方法
        void delete(int pos);
        //收藏数据的方法
        void collect(int pos);  //表示按照的是每一个页面的对应的数字显示
        //颜色增加的方法
        void addColor(int pos);  //表示的是对单词的重点标识程度(不同的标识程度对应不同的显示颜色)
    }


    private DeleteItem delItemListener;

    // 设置监听器的方法
    public void setDelItemListener(DeleteItem delItemListener){
        this.delItemListener = delItemListener;
    }
}

