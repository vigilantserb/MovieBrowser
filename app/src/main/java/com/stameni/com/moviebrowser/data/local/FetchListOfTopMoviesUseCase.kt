package com.stameni.com.moviebrowser.data.local

import com.stameni.com.moviebrowser.data.models.ListItem

class FetchListOfTopMoviesUseCase {

    fun getData(): List<ListItem> =
        ArrayList<ListItem>().apply {
            this.add(
                ListItem(
                    "The DC Comics Universe",
                    "/f6ljQGv7WnJuwBPty017oPWfqjx.jpg",
                    "3"
                )
            )
            this.add(
                ListItem(
                    "Best picture Winners - The Academy awards",
                    "/rgyhSn3mINvkuy9iswZK0VLqQO3.jpg",
                    "28"
                )
            )
            this.add(
                ListItem(
                    "Greatest Twist Ending",
                    "/ld7huGVoyXAY6EsdAUeLh9QxAl6.jpg",
                    "1131"
                )
            )
            this.add(
                ListItem(
                    "IMDb Top 250",
                    "/8LZ0r7r2SdJIApRvGo2k6CXHq8x.jpg",
                    "1309"
                )
            )
            this.add(
                ListItem(
                    "Best Picture Winners - The Golden Globes",
                    "/tNpJuz8NEG0DsGG8SN0dL2kbCzs.jpg",
                    "2469"
                )
            )
            this.add(
                ListItem(
                    "Disney Classic Collection",
                    "/jxWA2lDLidicmmDymnxlz53zAb9.jpg",
                    "338"
                )
            )
            //NEW
            this.add(
                ListItem(
                    "Oscar - best picture winners",
                    "/pzmrKXQgL7GEZvigD6W1bUEzXJN.jpg",
                    "113560"
                )
            )
            this.add(
                ListItem(
                    "24 Great Psychedelic Movies That Are Worth Your Time",
                    "/tdgsBYmNCZAkxYQZuUVwVl56Yv1.jpg",
                    "119015"
                )
            )
            this.add(
                ListItem(
                    "The Golden Globes - Best Picture Winners",
                    "/6xKCYgH16UuwEGAyroLU6p8HLIn.jpg",
                    "102340"
                )
            )
            this.add(
                ListItem(
                    "Best Psychological Thrillers",
                    "/s2bT29y0ngXxxu2IA8AOzzXTRhd.jpg",
                    "34805"
                )
            )
            this.add(
                ListItem(
                    "The best horror films of all time",
                    "/dfNrZ82poQ8blHWJreIv6JZQ9JA.jpg",
                    "8775"
                )
            )
            this.add(
                ListItem(
                    "Most Mind-bending Movies",
                    "/mMZRKb3NVo5ZeSPEIaNW9buLWQ0.jpg",
                    "34806"
                )
            )
            this.add(
                ListItem(
                    "30 Movies With A 100% Rating On Rotten Tomatoes",
                    "/2OgxK3jTzi72EebK8V3bWiymaIW.jpg",
                    "119018"
                )
            )
            this.add(
                ListItem(
                    "The 10 Best Movies About Humanity's Relationship With Technology",
                    "/pckdZ29bHj11hBsV3SbVVfmCB6C.jpg",
                    "119017"
                )
            )
        }.shuffled()
}