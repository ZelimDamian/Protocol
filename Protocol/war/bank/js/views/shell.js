directory.ShellView = Backbone.View.extend({

    initialize: function () {
    	_.bindAll(this, 'render', 'search');

    },

    render: function () {
    	var self = this;
        this.$el.html(this.template());
        return this;
    },

    events: {
        "keyup .search-query": "search",
        "keypress .search-query": "onkeypress"
    },

    search: function (event) {
        //var key = $('#searchText').val();
        //this.searchResults.fetch({reset: true, data: {name: key}});

    },

    onkeypress: function (event) {
        if (event.keyCode === 13) { // enter key pressed
            event.preventDefault();
        }
    },

    selectMenuItem: function(menuItem) {
        $('.navbar .nav li').removeClass('active');
        if (menuItem) {
            $('.' + menuItem).addClass('active');
        }
    }

});