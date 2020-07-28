/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.librarymanagement;

/**
 *
 * @author Suraj Upadhyay
 */
class Student {
    private String ScholarNo,Name,Branch,DOB,Mem_Start,Mem_End,Gender;
    private int Sem;
    public Student(String ScholarNo,String Name,String Branch,String DOB,String Mem_Start,String Mem_End,String Gender,int Sem){
        this.ScholarNo=ScholarNo;
        this.Name=Name;
        this.Branch=Branch;
        this.DOB=DOB;
        this.Mem_End=Mem_End;
        this.Mem_Start=Mem_Start;
        this.Gender=Gender;
        this.Sem=Sem;
    }
    String getScholarNo(){return ScholarNo;}
    String getName(){return Name;}
    String getBranch(){return Branch;}
    String getDOB(){return DOB;}
    String getMem_Start(){return Mem_Start;}
    String getMem_End(){return Mem_End;}
    String getGender(){return Gender;}
    int getSem(){return Sem;}
}
