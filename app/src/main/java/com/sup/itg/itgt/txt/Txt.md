#### 常用方法

> 自定义方法常用
```markdown
        int wMode = MeasureSpec.getMode(widthMeasureSpec);
        int wSize = MeasureSpec.getSize(widthMeasureSpec);
        int hMode = MeasureSpec.getMode(heightMeasureSpec);
        int hSize = MeasureSpec.getSize(heightMeasureSpec);
        
        if (wMode == MeasureSpec.EXACTLY) {//match_parent或者具体大小
                 
         } else {//wrap_content
                   
         }
         if (hMode == MeasureSpec.EXACTLY) {
                   
         } else {
                   
         }
        
         setMeasuredDimension(width, height);
```