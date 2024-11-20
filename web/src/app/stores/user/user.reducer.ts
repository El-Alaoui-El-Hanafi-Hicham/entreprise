import { createReducer,on } from "@ngrx/store"
import { EmployeeModule } from "src/app/modules/employeeModule/employee/employee.module"
import { loadData, loadDataFailure, setUser } from "./user.actions";

export interface ActiveUserState{
    emp:EmployeeModule;
    loading:boolean
};
export const initialState:ActiveUserState ={
    emp:{
        id: undefined,
        email: undefined,
        hire_date: undefined,
        first_name: undefined,
        last_name: undefined,
        phone_number: undefined,
        job_title: undefined,
        department_id: undefined
    },
    loading:true
};
export const  userReducer = createReducer (
   initialState,
   on(setUser, (_state:ActiveUserState,{user:emp}) => {

    return {
        emp:emp,
        loading:false
    
    }
   }),
   on(loadDataFailure, (_state:ActiveUserState) => {
    
    return {
    ..._state,
    loading:false
    }
   }),
   on(loadData, (state) => 
{
    console.log("THE LOADING SHOULD BE ON TRUE")
    return {
    ...state,
    loading: true,
  }}),
)
