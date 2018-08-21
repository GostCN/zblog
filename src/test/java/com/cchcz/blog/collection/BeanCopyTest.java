package com.cchcz.blog.collection;

import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * created by zhangcheng 2018/8/18
 */
public class BeanCopyTest {
    public static void main(String[] args) {
        Foo foo = new Foo(1L);
        foo.getItems().add(new Item("item1"));
        foo.getItems().add(new Item("item2"));

        Foo fooSnapShot = new Foo(100L);
        BeanUtils.copyProperties(foo, fooSnapShot);

        //foo.setId(999L);
        foo.getItems().add(new Item("item3"));
        System.out.println("foo:+" + foo);
        System.out.println("fooSnapShot:+" + fooSnapShot);
        System.out.println("----------------------------");
        fooSnapShot.setId(99900L);
        fooSnapShot.getItems().add(new Item("item4"));
        System.out.println("foo:+" + foo);
        System.out.println("fooSnapShot:+" + fooSnapShot);


        System.out.println("******************************************");

        //Foo foo = new Foo(1L);
        //foo.getItems().add(new Item("item1"));
        //foo.getItems().add(new Item("item2"));
        //
        //Foo fooSnapShot = new Foo(100L);
        //BeanUtils.copyProperties(foo, fooSnapShot);
        //
        //fooSnapShot.setItems(new ArrayList<Item>(foo.getItems().size()));
        //for(int i = 0; i < foo.getItems().size(); i++){
        //    Item anItem = new Item("");
        //    BeanUtils.copyProperties(foo.getItems().get(i), anItem);
        //    fooSnapShot.getItems().add(anItem);
        //}
        //
        //foo.setId(999L);
        //System.out.println("fooSnapShot id field value is not changing as expected : " + fooSnapShot.getId());
        //foo.getItems().add(new Item("item3"));
        //System.out.println("fooSnapShot items value is is not changing : " + fooSnapShot.getItems());


    }
}

class Foo {
    private Long id;
    private List<Item> items;

    public Foo(Long id) {
        this.id = id;
        this.items = new ArrayList<Item>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "Foo{" +
                "id=" + id +
                ", items=" + items +
                '}';
    }
}


class Item {
    private String bar;

    public Item(String bar) {
        this.bar = bar;
    }

    public String getBar() {
        return bar;
    }

    public void setBar(String bar) {
        this.bar = bar;
    }

    @Override
    public String toString() {
        return "Item{" + "bar='" + bar + '\'' + '}';
    }
}
