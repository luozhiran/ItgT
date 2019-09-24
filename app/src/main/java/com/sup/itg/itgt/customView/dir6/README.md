

##### ShadowLayer(控件阴影)

> BaseShadowView

```markdown
public void setShadowLayer(float radius, float dx, float dy, int color);

```
radius :模糊半径,radius越大越模糊，越小越清晰，但是如果radius设置为0，则阴影消失不见

dx:阴影的横向偏移距离，正值向右偏移，负值向左偏移
dy:阴影的纵向偏移距离，正值向下偏移，负值向上偏移
color: 绘制阴影的画笔颜色，即阴影的颜色（对图片阴影无效）

> 注意：这里有一点需要非常注意的是setShadowLayer只有文字绘制阴影支持硬件加速，其它都不支持硬件加速，所以为了方便起见，我们需要在自定义控件中禁用硬件加速。
```markdown
 setLayerType(LAYER_TYPE_SOFTWARE, null);//对单独的View在运行时阶段禁用硬件加速
```

### SetMaskFilter之BlurMaskFilter实现发光效果 

> BaseMaskFilterView

> 与setShadowLayer一样，发光效果也是使用的高斯模糊，并且只会影响边缘部分图像，内部图像是不受影响的
  发光效果是无法指定发光颜色的，采用边缘部分的颜色取样来进行模糊发光。所以边缘是什么颜色，发出的光也就是什么颜色的。
  
  ```markdown
     public MaskFilter setMaskFilter(MaskFilter maskfilter) 
     public BlurMaskFilter(float radius, Blur style) 
       
```
> setLayerType(LAYER_TYPE_SOFTWARE, null);//对单独的View在运行时阶段禁用硬件加速

float radius：用来定义模糊半径，同样是高斯模糊算法。
Blur style：发光样式，有内发光、外发光、和内外发光，分别对应：Blur.INNER(内发光)、Blur.SOLID(外发光)、Blur.NORMAL(内外发光)、Blur.OUTER(仅发光部分可见)，