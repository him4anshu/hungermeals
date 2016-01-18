
;(function($, window, document, undefined) {
 var name = "jPages", instance = null, defaults = {
  containerID : "",
  first : false,
  previous : "← previous",
  next : "next →",
  last : false,
  links : "numeric",
  startPage : 1,
  perPage : 10,
  midRange : 5,
  startRange : 1,
  endRange : 1,
  keyBrowse : false,
  scrollBrowse : false,
  pause : 0,
  clickStop : false,
  delay : 50,
  direction : "forward",
  animation : "",
  fallback : 400,
  minHeight : true,
  callback : undefined
 };
 function Plugin(element, options) {
  this.options = $.extend({}, defaults, options);
  this._container = $("#" + this.options.containerID);
  if (!this._container.length)
   return;
  this.jQwindow = $(window);
  this.jQdocument = $(document);
  this._holder = $(element);
  this._nav = {};
  this._first = $(this.options.first);
  this._previous = $(this.options.previous);
  this._next = $(this.options.next);
  this._last = $(this.options.last);
  this._items = this._container.children(":visible");
  this._itemsShowing = $([]);
  this._itemsHiding = $([]);
  this._numPages = Math.ceil(this._items.length / this.options.perPage);
  this._currentPageNum = this.options.startPage;
  this._clicked = false;
  this._cssAnimSupport = this.getCSSAnimationSupport();
  this.init();
 }
 Plugin.prototype = {
  constructor : Plugin,
  getCSSAnimationSupport : function() {
   var animation = false, animationstring = 'animation', keyframeprefix = '', domPrefixes = 'Webkit Moz O ms Khtml'.split(' '), pfx = '', elm = this._container.get(0);
   if (elm.style.animationName)
    animation = true;
   if (animation === false) {
    for (var i = 0; i " + ".jp-invisible { visibility: hidden !important; } " + ".jp-hidden { display: none !important; }" + "";
   $(requiredStyles).appendTo("head");
   if (this._cssAnimSupport && this.options.animation.length)
    this._items.addClass("animated jp-hidden");
   else
    this._items.hide();
  },
  setNav : function() {
   var navhtml = this.writeNav();
   this._holder.each(this.bind(function(index, element) {
    var holder = $(element);
    holder.html(navhtml);
    this.cacheNavElements(holder, index);
    this.bindNavHandlers(index);
    this.disableNavSelection(element);
   }, this));
   if (this.options.keyBrowse)
    this.bindNavKeyBrowse();
   if (this.options.scrollBrowse)
    this.bindNavScrollBrowse();
  },
  writeNav : function() {
   var i = 1, navhtml;
   navhtml = this.writeBtn("first") + this.writeBtn("previous");
   for (; i ...";
    if (i > this.options.startRange && i ";
    else
     navhtml += "";
    switch(this.options.links) {
     case"numeric":
      navhtml += i;
      break;
     case"blank":
      break;
     case"title":
      var title = this._items.eq(i - 1).attr("data-title");
      navhtml += title !== undefined ? title : "";
      break;
    }
    navhtml += "";
    if (i === this.options.startRange || i === this._numPages - this.options.endRange)
     navhtml += "...";
   }
   navhtml += this.writeBtn("next") + this.writeBtn("last") + "";
   return navhtml;
  },
  writeBtn : function(which) {
   return this.options[which] !== false && !$(this["_" + which]).length ? "" + this.options[which] + "" : "";
  },
  cacheNavElements : function(holder, index) {
   this._nav[index] = {};
   this._nav[index].holder = holder;
   this._nav[index].first = this._first.length ? this._first : this._nav[index].holder.find("a.jp-first");
   this._nav[index].previous = this._previous.length ? this._previous : this._nav[index].holder.find("a.jp-previous");
   this._nav[index].next = this._next.length ? this._next : this._nav[index].holder.find("a.jp-next");
   this._nav[index].last = this._last.length ? this._last : this._nav[index].holder.find("a.jp-last");
   this._nav[index].fstBreak = this._nav[index].holder.find("span:first");
   this._nav[index].lstBreak = this._nav[index].holder.find("span:last");
   this._nav[index].pages = this._nav[index].holder.find("a").not(".jp-first, .jp-previous, .jp-next, .jp-last");
   this._nav[index].permPages = this._nav[index].pages.slice(0, this.options.startRange).add(this._nav[index].pages.slice(this._numPages - this.options.endRange, this._numPages));
   this._nav[index].pagesShowing = $([]);
   this._nav[index].currentPage = $([]);
  },
  bindNavHandlers : function(index) {
   var nav = this._nav[index];
   nav.holder.bind("click.jPages", this.bind(function(evt) {
    var newPage = this.getNewPage(nav, $(evt.target));
    if (this.validNewPage(newPage)) {
     this._clicked = true;
     this.paginate(newPage);
    }
    evt.preventDefault();
   }, this));
   if (this._first.length) {
    this._first.bind("click.jPages", this.bind(function() {
     if (this.validNewPage(1)) {
      this._clicked = true;
      this.paginate(1);
     }
    }, this));
   }
   if (this._previous.length) {
    this._previous.bind("click.jPages", this.bind(function() {
     var newPage = this._currentPageNum - 1;
     if (this.validNewPage(newPage)) {
      this._clicked = true;
      this.paginate(newPage);
     }
    }, this));
   }
   if (this._next.length) {
    this._next.bind("click.jPages", this.bind(function() {
     var newPage = this._currentPageNum + 1;
     if (this.validNewPage(newPage)) {
      this._clicked = true;
      this.paginate(newPage);
     }
    }, this));
   }
   if (this._last.length) {
    this._last.bind("click.jPages", this.bind(function() {
     if (this.validNewPage(this._numPages)) {
      this._clicked = true;
      this.paginate(this._numPages);
     }
    }, this));
   }
  },
  disableNavSelection : function(element) {
   if ( typeof element.onselectstart != "undefined")
    element.onselectstart = function() {
     return false;
    };
   else if ( typeof element.style.MozUserSelect != "undefined")
    element.style.MozUserSelect = "none";
   else
    element.onmousedown = function() {
     return false;
    };
  },
  bindNavKeyBrowse : function() {
   this.jQdocument.bind("keydown.jPages", this.bind(function(evt) {
    var target = evt.target.nodeName.toLowerCase();
    if (this.elemScrolledIntoView() && target !== "input" && target != "textarea") {
     var newPage = this._currentPageNum;
     if (evt.which == 37)
      newPage = this._currentPageNum - 1;
     if (evt.which == 39)
      newPage = this._currentPageNum + 1;
     if (this.validNewPage(newPage)) {
      this._clicked = true;
      this.paginate(newPage);
     }
    }
   }, this));
  },
  elemScrolledIntoView : function() {
   var docViewTop, docViewBottom, elemTop, elemBottom;
   docViewTop = this.jQwindow.scrollTop();
   docViewBottom = docViewTop + this.jQwindow.height();
   elemTop = this._container.offset().top;
   elemBottom = elemTop + this._container.height();
   return ((elemBottom >= docViewTop) && (elemTop  0 ? (this._currentPageNum - 1) : (this._currentPageNum + 1);
    if (this.validNewPage(newPage)) {
     this._clicked = true;
     this.paginate(newPage);
    }
    evt.preventDefault();
    return false;
   }, this));
  },
  getNewPage : function(nav, target) {
   if (target.is(nav.currentPage))
    return this._currentPageNum;
   if (target.is(nav.pages))
    return nav.pages.index(target) + 1;
   if (target.is(nav.first))
    return 1;
   if (target.is(nav.last))
    return this._numPages;
   if (target.is(nav.previous))
    return nav.pages.index(nav.currentPage);
   if (target.is(nav.next))
    return nav.pages.index(nav.currentPage) + 2;
  },
  validNewPage : function(newPage) {
   return newPage !== this._currentPageNum && newPage > 0 && newPage  this._items.length)
    range.end = this._items.length;
   return range;
  },
  cssAnimations : function(page) {
   clearInterval(this._delay);
   this._itemsHiding.removeClass(this.options.animation + " jp-invisible").addClass("jp-hidden");
   this._itemsShowing.removeClass("jp-hidden").addClass("jp-invisible");
   this._itemsOriented = this.getDirectedItems(page);
   this._index = 0;
   this._delay = setInterval(this.bind(function() {
    if (this._index === this._itemsOriented.length)
     clearInterval(this._delay);
    else {
     this._itemsOriented.eq(this._index).removeClass("jp-invisible").addClass(this.options.animation);
    }
    this._index = this._index + 1;
   }, this), this.options.delay);
  },
  jQAnimations : function(page) {
   clearInterval(this._delay);
   this._itemsHiding.addClass("jp-hidden");
   this._itemsShowing.fadeTo(0, 0).removeClass("jp-hidden");
   this._itemsOriented = this.getDirectedItems(page);
   this._index = 0;
   this._delay = setInterval(this.bind(function() {
    if (this._index === this._itemsOriented.length)
     clearInterval(this._delay);
    else {
     this._itemsOriented.eq(this._index).fadeTo(this.options.fallback, 1);
    }
    this._index = this._index + 1;
   }, this), this.options.delay);
  },
  getDirectedItems : function(page) {
   var itemsToShow;
   switch(this.options.direction) {
    case"backwards":
     itemsToShow = $(this._itemsShowing.get().reverse());
     break;
    case"random":
     itemsToShow = $(this._itemsShowing.get().sort(function() {
      return (Math.round(Math.random()) - 0.5);
     }));
     break;
    case"auto":
     itemsToShow = page >= this._currentPageNum ? this._itemsShowing : $(this._itemsShowing.get().reverse());
     break;
    default:
     itemsToShow = this._itemsShowing;
   }
   return itemsToShow;
  },
  updatePages : function(page) {
   var interval, index, nav;
   interval = this.getInterval(page);
   for (index in this._nav) {
    if (this._nav.hasOwnProperty(index)) {
     nav = this._nav[index];
     this.updateBtns(nav, page);
     this.updateCurrentPage(nav, page);
     this.updatePagesShowing(nav, interval);
     this.updateBreaks(nav, interval);
    }
   }
   return interval;
  },
  getInterval : function(page) {
   var neHalf, upperLimit, start, end;
   neHalf = Math.ceil(this.options.midRange / 2);
   upperLimit = this._numPages - this.options.midRange;
   start = page > neHalf ? Math.max(Math.min(page - neHalf, upperLimit), 0) : 0;
   end = page > neHalf ? Math.min(page + neHalf - (this.options.midRange % 2 > 0 ? 1 : 0), this._numPages) : Math.min(this.options.midRange, this._numPages);
   return {
    start : start,
    end : end
   };
  },
  updateBtns : function(nav, page) {
   if (page === 1) {
    nav.first.addClass("jp-disabled");
    nav.previous.addClass("jp-disabled");
   }
   if (page === this._numPages) {
    nav.next.addClass("jp-disabled");
    nav.last.addClass("jp-disabled");
   }
   if (this._currentPageNum === 1 && page > 1) {
    nav.first.removeClass("jp-disabled");
    nav.previous.removeClass("jp-disabled");
   }
   if (this._currentPageNum === this._numPages && page  this.options.startRange || (this.options.startRange === 0 && interval.start > 0))
    nav.fstBreak.removeClass("jp-hidden");
   else
    nav.fstBreak.addClass("jp-hidden");
   if (interval.end  1) {
    clearTimeout(this._pause);
    if (this.options.clickStop && this._clicked)
     return;
    else {
     this._pause = setTimeout(this.bind(function() {
      this.paginate(this._currentPageNum !== this._numPages ? this._currentPageNum + 1 : 1);
     }, this), this.options.pause);
    }
   }
  },
  setMinHeight : function() {
   if (this.options.minHeight && !this._container.is("table, tbody")) {
    setTimeout(this.bind(function() {
     this._container.css({
      "min-height" : this._container.css("height")
     });
    }, this), 1000);
   }
  },
  bind : function(fn, me) {
   return function() {
    return fn.apply(me, arguments);
   };
  },
  destroy : function() {
   this.jQdocument.unbind("keydown.jPages");
   this._container.unbind("mousewheel.jPages DOMMouseScroll.jPages");
   if (this.options.minHeight)
    this._container.css("min-height", "");
   if (this._cssAnimSupport && this.options.animation.length)
    this._items.removeClass("animated jp-hidden jp-invisible " + this.options.animation);
   else
    this._items.removeClass("jp-hidden").fadeTo(0, 1);
   this._holder.unbind("click.jPages").empty();
  }
 };
 $.fn[name] = function(arg) {
  var type = $.type(arg);
  if (type === "object") {
   if (this.length && !$.data(this, name)) {
    instance = new Plugin(this, arg);
    this.each(function() {
     $.data(this, name, instance);
    });
   }
   return this;
  }
  if (type === "string" && arg === "destroy") {
   instance.destroy();
   this.each(function() {
    $.removeData(this, name);
   });
   return this;
  }
  if (type === 'number' && arg % 1 === 0) {
   if (instance.validNewPage(arg))
    instance.paginate(arg);
   return this;
  }
  return this;
 };
})(jQuery, window, document);