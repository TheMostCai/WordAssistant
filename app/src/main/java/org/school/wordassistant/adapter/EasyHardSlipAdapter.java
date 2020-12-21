package org.school.wordassistant.adapter;


import android.content.Context;
import android.util.Log;
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

public class EasyHardSlipAdapter extends BaseAdapter{

    private LayoutInflater inflater;
    private List<List<Object>> list;
    private Context context;

    public EasyHardSlipAdapter(Context context, List<List<Object>> list) {
        this.inflater = LayoutInflater.from(context);
        this.list = list;
        this.context = context;
    }


    //设置一个setter和getter方法实现数据的更新
    public List<List<Object>> getList() {
        return list;
    }

    public void setList(List<List<Object>> list) {
        this.list = list;
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
            convertView = inflater.inflate(R.layout.listview_item_easyhardwords, parent, false);
            holder = new ViewHolder();
            holder.eh_tv_EN = convertView.findViewById(R.id.eh_tv_EN);
            holder.eh_btnDelete = convertView.findViewById(R.id.eh_btnDelete);
            holder.eh_tv_number = convertView.findViewById(R.id.eh_tv_number);  //设置number数组
            holder.eh_tv_CN = convertView.findViewById(R.id.eh_tv_CN);  //设置中文解释显示
            convertView.setTag(holder);
        }


        if (closeView == null){  //表示判断closeView是不是空的,如果是那么就将convertView赋值过去
            closeView = convertView;
        }


        final View finalCloseView = closeView;  // listView的itemView

        //得到的是对应的原来的holder对象
        holder = (ViewHolder) convertView.getTag();

//        Log.i("EHSA get position --> ",(((List<Object>)(list.get(position))).size()+""));
//        Log.i("EHSA get EN --> ",((Word)((list.get(position)).get(1))).getWordString());
//        Log.i("EHSA get CN --> ",((Word)(list.get(position)).get(1)).getDescription());

        //向listview组件item中对应设置数据
        holder.eh_tv_EN.setText(((Word)((list.get(position)).get(1))).getWordString());

        //向listView中的tv_CN组件设置对应的中文解释
        holder.eh_tv_CN.setText(((Word)(list.get(position).get(1))).getDescription());

        //向listView中的组件tv_number设置对应的单词序号
        holder.eh_tv_number.setText((position+1)+"");


        // 删除按钮的单击事件
        holder.eh_btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(context, "已删除,请前往熟词本查看", Toast.LENGTH_SHORT).show();

                if (delItemListener != null){  //显示判断监听器接口是不是空的,如果不是的直接调用接口中的删除方法
                    delItemListener.delete(position); // 调用接口的方法，回调删除该项数据(是按照list中的index下标删除数据)
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
        TextView eh_tv_EN;// 展示内容
        Button eh_btnDelete;// 移除按钮
        TextView eh_tv_number;//设置单词序号
        TextView eh_tv_CN; //设置的单词的解释
    }


    // 定义接口，包含了对于数据操作的方法
    public interface DeleteItem{
        //删除数据的方法
        void delete(int pos);

        //收藏数据的方法
//        void collect(int pos);  //表示按照的是每一个页面的对应的数字显示
//        //颜色增加的方法
//        void addColor(int pos);  //表示的是对单词的重点标识程度(不同的标识程度对应不同的显示颜色)

    }

    private DeleteItem delItemListener;

    // 设置监听器的方法
    public void setDelItemListener(DeleteItem delItemListener){
        this.delItemListener = delItemListener;
    }
}


