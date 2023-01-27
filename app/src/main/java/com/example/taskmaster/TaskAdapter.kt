package com.example.taskmaster

import android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
import android.icu.text.CaseMap.Title
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.task_item.view.*

class TaskAdapter(
    private val tasks: MutableList<Task>
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {
    class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder{
        return TaskViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.task_item,
                parent,
                false
            )
        )
    }

    private fun toggleStrikeThrough(tvTaskTitle: TextView, isChecked: Boolean){
        if(isChecked){
            tvTaskTitle.paintFlags = STRIKE_THRU_TEXT_FLAG
        } else {
            tvTaskTitle.paintFlags = STRIKE_THRU_TEXT_FLAG.inv()
        }
    }

    fun addTask(task: Task){
        tasks.add(task)
        notifyItemInserted(tasks.size - 1)
    }

    fun deleteDoneTasks(){
        tasks.removeAll{ task ->
            task.isChecked
        }
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int){
        val curTask = tasks[position]
        holder.itemView.apply{
            tvTaskTitle.text = curTask.title
            cbDone.isChecked = curTask.isChecked
            toggleStrikeThrough(tvTaskTitle, curTask.isChecked)
            cbDone.setOnCheckedChangeListener{_, isChecked ->
                toggleStrikeThrough(tvTaskTitle, isChecked)
                curTask.isChecked = !curTask.isChecked
            }
        }
    }

    override fun getItemCount(): Int {
        return tasks.size
    }

}