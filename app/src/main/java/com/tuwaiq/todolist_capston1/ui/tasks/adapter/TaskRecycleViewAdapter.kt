package com.tuwaiq.todolist_capston1.ui.tasks

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.tuwaiq.todolist_capston1.R
import com.tuwaiq.todolist_capston1.model.Task
import com.tuwaiq.todolist_capston1.ui.tasks.modelView.taskViewModel
import kotlinx.android.synthetic.main.fragment_task_details.view.*
import kotlinx.android.synthetic.main.item_task.view.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class TaskRecycleViewAdapter(
    private var taskList: List<Task>,
    val viewModel: taskViewModel,
    private val isLandscape: Boolean
) : RecyclerView.Adapter<TaskRecycleViewAdapter.CustomAdapter.ViewHolder>() {
    //   private val repo = TaskRepo(this)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomAdapter.ViewHolder {
        //parent is the recycle layout which will get injected with views (items)
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return CustomAdapter.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: CustomAdapter.ViewHolder, position: Int) {
        val task = taskList[position]
        holder.tv_TaskName.text = "${task.taskTitle}"
        holder.tv_DueDate.text = "${task.due_date}"
        holder.checkB.isChecked = task.taskCompleted
        //  holder.tv_CreationDate.text = "${task.created_date}"
        /*if (task.taskCompleted == true) {
            holder.checkB.isChecked = true
        }*/

        if (task.important) {
            holder.imagImportant.isVisible = true
        }
        else holder.imagImportant.isVisible = false



        // comparing the date
        val current = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd")
        val formatted = current.format(formatter)

        val creationDate = formatted

       /* if (task.due_date >= creationDate) {
            holder.checkB.isEnabled = true


        } else {
            holder.checkB.isEnabled = false

        }*/

        val cardV = holder.itemView.findViewById<CardView>(R.id.itemID)
        val newcolor = holder.itemView.resources.getColor(R.color.green)
        val oldcolor = holder.itemView.resources.getColor(R.color.white)
       //cardV.setCardBackgroundColor(newcolor)

// change value in database
        //holder.checkB.isChecked
        holder.checkB.setOnCheckedChangeListener { _, ischeeck ->
            if (ischeeck) {
                task.taskCompleted = true
                cardV.setCardBackgroundColor(newcolor)
                viewModel.update(task)
            } else {
                task.taskCompleted = false
                cardV.setCardBackgroundColor(oldcolor)

                viewModel.update(task)
            }
        }
        task.taskCompleted = holder.checkB.isChecked


        holder.itemView.setOnClickListener { view ->
            val action = TaskFragmentDirections.actionTaskFragmentToTaskDetailsFragment(task)
            view.findNavController().navigate(action)
        }

    }

    override fun getItemCount(): Int {
        return taskList.size
    }
/*
    fun setData(task: List<Task>){
        taskList =task
        notifyDataSetChanged()

    }*/


    class CustomAdapter {
        class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
            View.OnClickListener {
            // item view here is from the on create function where we created in the view
            val tv_TaskName: TextView = itemView.findViewById(R.id.text_view_name)
            val tv_DueDate: TextView = itemView.findViewById(R.id.tvDueDateItem)
            val checkB: CheckBox = itemView.findViewById(R.id.check_boc_completed)
            val imagImportant: ImageView = itemView.findViewById(R.id.label_piority)

            init {
                itemView.setOnClickListener(this)

            }

            override fun onClick(view: View?) {
                Toast.makeText(itemView.context, "${tv_TaskName.text} cliced", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    fun convertDateToString(Date: LocalDateTime): String {
        val localDate: LocalDateTime = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yy-MM-dd HH:mm")
        val formattedCreationDate: String = localDate.format(formatter)
        return formattedCreationDate
    }
}

