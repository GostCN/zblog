var Marker = (function () {
    function Marker(map, lat, lng, address) {
        this.map = map;
        this.lat = lat;
        this.lng = lng;
        var point = this.map.mapObj.latLngToPoint(this.lat, this.lng);
        this.x = point.x;
        this.y = point.y;
        this.address = address;
    }

    Marker.prototype.animate = function () {
        var marker_animation = $('<div class="marker_animation"></div>').css('left', this.x + 'px').css('top', this.y + 'px').css({
            opacity: .5,
            scale: 0
        });
        this.map.mapElem.append(marker_animation);
        marker_animation
            .transition({
                opacity: 0,
                scale: 1
            }, 1000, 'linear', function () {
                return $(this).remove();
            });
        return;
    };

    Marker.prototype.id = function () {
        return this.lat + "," + this.lng;
    };

    Marker.prototype.gps = function () {
        return [this.lat, this.lng];
    };

    Marker.prototype.location = function () {
        return this.address;
    };

    return Marker;

})();

var VisitorMap = (function () {
    function VisitorMap(ele, config) {
        this.config = config;
        this.hits = {
            marker: {
                total: 0
            }
        };
        this.mapElem = $(ele);
        this.mapElem.vectorMap({
            map: 'cn_merc_en',
            backgroundColor: '#385868',
            normalizeFunction: 'polynomial',
            markersSelectable: true,
            regionStyle: {
                initial: {
                    'fill': '#FFFFFF',
                    'fill-opacity': 0.3,
                    'stroke': '#385868',
                    'stroke-width': 0.7,
                    'stroke-opacity': 0.5
                },
                hover: {
                    'fill-opacity': 0.5,
                    'fill': '#DDDDDD'
                }
            },
            markerStyle: {
                initial: {
                    'fill': '#009a61',
                    'stroke': '#FFFFFF',
                    'stroke-width': 1,
                    'r': 3
                },
                selected: {
                    'fill': '#7C38BC',
                    'stroke-width': 1
                }
            },
            markers: markers || []
        });
        this.mapObj = this.mapElem.vectorMap('get', 'mapObject');
    }

    VisitorMap.prototype.removeOldestMarker = function () {
        var id, par, toremove;
        toremove = $(this.mapElem.find("svg g circle.jvectormap-marker")[0]);
        par = toremove.parent();
        id = toremove.attr('data-index');
        this.mapObj.removeMarkers([id]);
        return par.remove();
    };

    VisitorMap.prototype.addMarker = function (marker) {
        marker.animate();
        if (this.mapObj.markers[marker.id()]) {
            return;
        }
        this.hits.marker["total"]++;
        if (this.hits.marker["total"] > this.config.markersMaxVisible) {
            this.removeOldestMarker();
        }
        return this.mapObj.addMarker(marker.id(), {
            latLng: marker.gps(),
            name: marker.location()
        });
    };

    return VisitorMap;

})();


var map = new VisitorMap('#js_visitor_map', {markersMaxVisible: 1000});

var sound = new Howl({
    urls: ['/sound/tering.mp3']
});
sound.play();

if (!!window.EventSource) {
    var source = new EventSource('/visitor/event');
    source.addEventListener('visitor', function (e) {
        console.log(e.data);
        var d = JSON.parse(e.data);
        if (!d.location) return;
        var marker = new Marker(map, d.location.lat, d.location.lng, d.address);
        map.addMarker(marker);
        sound.play();
    }, false);
}
function refreshMap(visitjson) {
    var d = JSON.parse(visitjson);
    if (!d.location) return;
    var marker = new Marker(map, d.location.lat, d.location.lng, d.address);
    map.addMarker(marker);
    sound.play();
}