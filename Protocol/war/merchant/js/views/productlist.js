directory.ProductListView = Backbone.View.extend({
   
    initialize:function () {
    	_.bindAll(this);
    	this.model.on("reset", this.render, this);
        this.model.on("add", function (product) {
            this.$el.append(new directory.ProductListItemView({model:product}).render().el);
        }, this);
    },

    render:function () {
        this.$el.empty();
        _.each(this.model.models, function (product) {
            this.$el.append(new directory.ProductListItemView({model:product}).render().el);
        }, this);
        return this;
    }
});

directory.ProductListItemView = Backbone.View.extend({
    events:{
    	'click' : function(element) {
    			window.location = "#products/" + this.model.id;
    		}
    },
    
    initialize:function () {
    	_.bindAll(this);
        this.model.on("destroy", this.close, this);
        this.model.on("sync", this.sync, this);
    },
    
    sync:function()
    {
    	this.render();
    	window.location = '#products/' + this.model.id;
    },
    
    render:function () {
        this.$el.html(this.template(this.model.toJSON()));
        return this;
    }

});