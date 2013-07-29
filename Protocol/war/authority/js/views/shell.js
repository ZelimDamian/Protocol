directory.ShellView = Backbone.View.extend({

    initialize: function () {
    	_.bindAll(this);
    },

    render: function () {
        this.$el.html(this.template());
        return this;
    },

    selectMenuItem: function(menuItem) {
        $('.navbar .nav li').removeClass('active');
        if (menuItem) {
            $('.' + menuItem).addClass('active');
        }
    }

});