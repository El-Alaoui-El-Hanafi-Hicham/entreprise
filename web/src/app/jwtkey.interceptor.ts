import { HttpInterceptorFn } from '@angular/common/http';

export const jWTKeyInterceptor: HttpInterceptorFn = (req, next) => {
  let token: string = localStorage.getItem('key') ?? "";
  let customReq=req.clone({
    headers: req.headers.set('Authorization', `Bearer ${token}`).set("Content-Type","application/json").set(
  "Accept","application/json")
  })
  return next(customReq);
};
