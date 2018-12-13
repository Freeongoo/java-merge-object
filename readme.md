# Merge Object

## Problem

Often there is the task of merging the fields of two objects of the same class.

As a rule, this problem is solved as follows:

```
Cat catTo = new Cat(2);
Cat catFrom = new Cat(3);

catTo.setAge(catTo.getAge() + catFrom.getAge());

System.out.println(catTo.getAge()); // 5

class Cat {
    private Integer age;

    public Cat(Integer age) {
        this.age = age;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
```

But this approach is very inconvenient. When increasing the number of fields - 
increases duplication of the code. 
Also, if one of the fields contains null, then an exception will be thrown.

## Solution

To solve this problem and get rid of code duplication, use our class MergeObject:

```
MergeObject mergeObject = new MergeObjectImpl();
Set<String> fields = new HashSet<>(Arrays.asList("age"));
mergeObject.sumNumberFields(catTo, catFrom, fields);

System.out.println(catTo.getAge());
```