package com.example.guide

import com.example.guide.data.ClassStudent
import com.example.guide.data.ClassTeacher

class LocalTestData {
    companion object{
        val classTeacherList = arrayListOf<ClassTeacher>(
                ClassTeacher("2020-2021-2","2020-2","测试1","福大","数计学院","00000001"),
                ClassTeacher("2020-2021-2","2020-2","测试2","福大","数计学院","00000002"),
                ClassTeacher("2020-2021-2","2020-2","测试3","福大","数计学院","00000003"),
                ClassTeacher("2020-2021-2","2020-2","测试4","福大","数计学院","00000004"),
                ClassTeacher("2020-2021-2","2020-2","测试5","福大","数计学院","00000005"))
        val classStudentList = arrayListOf<ClassStudent>(
                ClassStudent("2020-2021-2","2020-2","测试1","福大","数计学院","00000001","测试教师"),
                ClassStudent("2020-2021-2","2020-2","测试2","福大","数计学院","00000002","测试教师"),
                ClassStudent("2020-2021-2","2020-2","测试3","福大","数计学院","00000003","测试教师"),
                ClassStudent("2020-2021-2","2020-2","测试4","福大","数计学院","00000004","测试教师"),
                ClassStudent("2020-2021-2","2020-2","测试5","福大","数计学院","00000005","测试教师"),
        )
    }
}