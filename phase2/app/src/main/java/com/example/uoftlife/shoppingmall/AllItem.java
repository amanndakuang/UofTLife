package com.example.uoftlife.shoppingmall;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

class AllItem implements Iterable {

    private ArrayList<String> items = new ArrayList<>();

    AllItem() {
        for (int i = 0; i < 12; i++) {
            double r = Math.random();
            if (r < 0.2) {
                items.add("bread");
            } else if (0.2 <= r && r < 0.4) {
                items.add("coffee");
            } else if (0.4 <= r && r < 0.6) {
                items.add("bubbleTea");
            } else if (0.6 <= r && r < 0.8) {
                items.add("book");
            } else {
                items.add("lipstick");
            }
        }
    }


    public Iterator iterator() {
        return new itemIterator();
    }

    class itemIterator implements Iterator<String> {

        private int next;


        @Override
        public boolean hasNext() {
            return next < items.size();
        }


        @Override
        public String next() {
            if (hasNext()) {
                return items.get(next++);
            }
            throw new NoSuchElementException();
        }

    }

}