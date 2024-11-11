import { createReducer,on } from "@ngrx/store"
import { EmployeeModule } from "src/app/modules/employeeModule/employee/employee.module"
import { setUser } from "./user.actions";

export const initialState:EmployeeModule| undefined | object ={};
export const  userReducer = createReducer (
   initialState,
   on(setUser, (state,payload) => {
    console.log("payload is ===>",payload)
    return {
        ...payload
    }
   }),
)