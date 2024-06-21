package com.gildedrose;

class GildedRose {
    Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    public void updateQuality() {
        for (Item item : items) {
            updateItemQuality(item);
            updateItemSellIn(item);
            handleExpiredItem(item);
        }
    }

    private void updateItemQuality(Item item) {
        switch (item.name) {
            case "Aged Brie":
                increaseQuality(item);
                break;
            case "Backstage passes to a TAFKAL80ETC concert":
                increaseQuality(item);
                if (item.sellIn < 11) {
                    increaseQuality(item);
                }
                if (item.sellIn < 6) {
                    increaseQuality(item);
                }
                break;
            case "Sulfuras, Hand of Ragnaros":
                break;
            case "Conjurado":
            	decreaseQualityConjured(item);
            	break;
            default:
                decreaseQuality(item);
                break;
        }
    }

    private void updateItemSellIn(Item item) {
        if (!item.name.equals("Sulfuras, Hand of Ragnaros")) {
            item.sellIn--;
        }
    }

    private void handleExpiredItem(Item item) {
        if (item.sellIn < 0) {
            switch (item.name) {
                case "Aged Brie":
                    increaseQuality(item);
                    break;
                case "Backstage passes to a TAFKAL80ETC concert":
                    item.quality = 0;
                    break;
                case "Sulfuras, Hand of Ragnaros":
                    break;
                case "Conjurado":
                	decreaseQualityConjured(item);
                	break;
                default:
                    decreaseQuality(item);
                    break;
            }
        }
    }

    private void decreaseQualityConjured(Item item) {
        if (item.quality > 0) {
            item.quality = item.quality-2 ;
        }		
	}

	private void increaseQuality(Item item) {
        if (item.quality < 50) {
            item.quality++;
        }
    }

    private void decreaseQuality(Item item) {
        if (item.quality > 0) {
            item.quality--;
        }
    }
}
