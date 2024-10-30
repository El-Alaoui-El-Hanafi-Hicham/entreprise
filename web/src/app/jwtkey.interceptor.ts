import { HttpInterceptorFn } from '@angular/common/http';

export const jWTKeyInterceptor: HttpInterceptorFn = (req, next) => {
  let token: string = localStorage.getItem('key') ?? "";
  console.log(req.headers.get('Content-Type'))
  let customReq=req.clone({
    headers: req.headers.set('Authorization', `Bearer ${token}`)
  })
  return next(customReq);
};
